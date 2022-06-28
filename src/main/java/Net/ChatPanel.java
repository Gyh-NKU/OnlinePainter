package Net;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class ChatPanel extends JPanel {

    public JTextArea jTextArea = new JTextArea();
    public JTextPane jTextPane = new JTextPane();
    public JButton sendButton = new JButton("发送");
    public JButton clearButton = new JButton("清屏");
    public String name;

    public ChatPanel(Socket chatSocket, String name) {
        this.name = name;
        setLayout(new BorderLayout());
        JPanel messageArea = new JPanel();
        messageArea.setLayout(new BorderLayout());



        jTextPane.setPreferredSize(new Dimension(300, 400));
        jTextArea.setPreferredSize(new Dimension(300, 200));

        JPanel editSend = new JPanel();
        editSend.setLayout(new BorderLayout());
        editSend.add(jTextArea, BorderLayout.CENTER);
        JPanel buttons = new JPanel();
        buttons.add(sendButton);
        buttons.add(clearButton);
        editSend.add(buttons,BorderLayout.SOUTH);
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jTextPane.setText("");
            }
        });

        jTextPane.setBorder(BorderFactory.createTitledBorder("聊天区"));
        jTextArea.setBorder(BorderFactory.createTitledBorder("输入要发送的内容"));
        jTextArea.setLineWrap(true);
        jTextPane.setEditable(false);
        jTextArea.setFont(new Font("Serif", Font.PLAIN, 15));
        jTextPane.setFont(new Font("Serif", Font.PLAIN, 15));
        add(jTextPane, BorderLayout.CENTER);
        add(editSend, BorderLayout.SOUTH);

        setVisible(true);
        new Thread() {
            @Override
            public void run() {
                try {
                    try (InputStream is = chatSocket.getInputStream()) {
                        try (OutputStream os = chatSocket.getOutputStream()) {
                            handle(is, os);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

//    public static void main(String[] args) throws IOException {
//        JFrame frame = new JFrame();
//        frame.setContentPane(new ChatClient());
//        frame.setSize(400, 600);
//        frame.setVisible(true);
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
////        frame.setSize(400, 800);
//    }

    private void appendText(String s) {
        appendText(s, Color.black);
    }

    private void appendText(String s, Color c) {
        Random rd = new Random();

        StyledDocument doc = jTextPane.getStyledDocument();

        Style style = jTextPane.addStyle("" + rd.nextInt(), null);

        StyleConstants.setForeground(style, c);

        try {
            doc.insertString(doc.getLength(), s, style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

    }

    private void appendTextLn(String s) {
        appendText(s + "\n", Color.black);
    }

    private void appendTextLn(String s, Color c) {
        appendText(s + "\n", c);
    }

    private void handle(InputStream input, OutputStream output) throws IOException {
        var writer = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
        var reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    writer.write("bye");
                    writer.newLine();
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        writer.write(name);
        writer.newLine();
        writer.flush();
//        System.out.println("[server] " + reader.readLine());

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = jTextArea.getText();
                try {
                    writer.write(s);
                    writer.newLine();
                    writer.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } finally {
                    jTextArea.setText("");
                }
            }
        });
        jTextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (c == '\n') {
                    String s = jTextArea.getText();
                    s = s.substring(0, s.length() - 1);
                    try {
                        writer.write(s);
                        writer.newLine();
                        writer.flush();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } finally {
                        jTextArea.setText("");
                    }
                }
            }
        });
        jTextArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (e.getDocument().getLength() != 0) {
                    sendButton.setEnabled(true);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (e.getDocument().getLength() == 0) {
                    sendButton.setEnabled(false);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (e.getDocument().getLength() != 0) {
                    sendButton.setEnabled(true);
                }
            }
        });
        for (; ; ) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            String resp = reader.readLine();
            if (resp.equals("[server]")) {
                String s = reader.readLine();
                appendTextLn("系统消息： " + s, Color.BLUE);
                continue;
            } else if (resp.matches("^\\[.+\\]$")) {
                resp = resp.substring(1, resp.length() - 1);
                appendText(String.format("[%s] ", LocalDateTime.now().format(dtf)), new Color(98, 151, 85));
                appendTextLn(resp + "： ", new Color(98, 151, 85));
                resp = reader.readLine();
                appendTextLn(resp);
            }
            if ("bye".equals(resp)) {
                break;
            }
        }
    }
}