package library;

import javax.swing.*;
import java.awt.*;

public class LibraryGUI {

    static Library library = new Library();

    public static void main(String[] args) {

        library.loadBooks();

        JFrame frame = new JFrame("Library Management System");
        frame.setSize(500,550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(40,40,40));

        JLabel title = new JLabel("Library Dashboard", JLabel.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial",Font.BOLD,22));

        mainPanel.add(title,BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(7,1,10,10));
        buttonPanel.setBackground(new Color(40,40,40));

        JButton addBook = new JButton("Add Book");
        JButton showBooks = new JButton("Show Books");
        JButton borrowBook = new JButton("Borrow Book");
        JButton returnBook = new JButton("Return Book");
        JButton searchBook = new JButton("Search Book");
        JButton editBook = new JButton("Edit Book");
        JButton deleteBook = new JButton("Delete Book");

        buttonPanel.add(addBook);
        buttonPanel.add(showBooks);
        buttonPanel.add(borrowBook);
        buttonPanel.add(returnBook);
        buttonPanel.add(searchBook);
        buttonPanel.add(editBook);
        buttonPanel.add(deleteBook);

        mainPanel.add(buttonPanel,BorderLayout.CENTER);

        JPanel statsPanel = new JPanel(new GridLayout(1,3));
        statsPanel.setBackground(new Color(60,60,60));

        JLabel totalBooks = new JLabel("",JLabel.CENTER);
        JLabel availableBooks = new JLabel("",JLabel.CENTER);
        JLabel borrowedBooks = new JLabel("",JLabel.CENTER);

        totalBooks.setForeground(Color.WHITE);
        availableBooks.setForeground(Color.WHITE);
        borrowedBooks.setForeground(Color.WHITE);

        statsPanel.add(totalBooks);
        statsPanel.add(availableBooks);
        statsPanel.add(borrowedBooks);

        mainPanel.add(statsPanel,BorderLayout.SOUTH);

        frame.add(mainPanel);

        updateStats(totalBooks,availableBooks,borrowedBooks);

        addBook.addActionListener(e -> {

            String idStr = JOptionPane.showInputDialog(frame,"Enter Book ID:");
            if(idStr == null || idStr.trim().isEmpty()) return;

            String titleStr = JOptionPane.showInputDialog(frame,"Enter Title:");
            if(titleStr == null || titleStr.trim().isEmpty()) return;

            String authorStr = JOptionPane.showInputDialog(frame,"Enter Author:");
            if(authorStr == null || authorStr.trim().isEmpty()) return;

            try{

                int id = Integer.parseInt(idStr);

                Book book = new Book(id,titleStr,authorStr);
                library.addBook(book);

                JOptionPane.showMessageDialog(frame,"Book Added");

                updateStats(totalBooks,availableBooks,borrowedBooks);

            }catch(Exception ex){

                JOptionPane.showMessageDialog(frame,"Invalid ID");

            }

        });

        showBooks.addActionListener(e -> {

            String[] columns = {"ID","Title","Author","Borrowed"};

            Object[][] data = new Object[library.books.size()][4];

            for(int i=0;i<library.books.size();i++){

                Book b = library.books.get(i);

                data[i][0] = b.id;
                data[i][1] = b.title;
                data[i][2] = b.author;
                data[i][3] = b.isBorrowed ? "Yes":"No";

            }

            JTable table = new JTable(data,columns);

            JScrollPane scroll = new JScrollPane(table);
            scroll.setPreferredSize(new Dimension(400,250));

            JOptionPane.showMessageDialog(frame,scroll,"Library Books",
                    JOptionPane.INFORMATION_MESSAGE);

        });

        borrowBook.addActionListener(e -> {

            String idStr = JOptionPane.showInputDialog(frame,"Enter Book ID:");
            if(idStr == null || idStr.trim().isEmpty()) return;

            try{

                int id = Integer.parseInt(idStr);
                library.borrowBook(id);

                updateStats(totalBooks,availableBooks,borrowedBooks);

            }catch(Exception ex){

                JOptionPane.showMessageDialog(frame,"Invalid ID");

            }

        });

        returnBook.addActionListener(e -> {

            String idStr = JOptionPane.showInputDialog(frame,"Enter Book ID:");
            if(idStr == null || idStr.trim().isEmpty()) return;

            try{

                int id = Integer.parseInt(idStr);
                library.returnBook(id);

                updateStats(totalBooks,availableBooks,borrowedBooks);

            }catch(Exception ex){

                JOptionPane.showMessageDialog(frame,"Invalid ID");

            }

        });

        searchBook.addActionListener(e -> {

            String idStr = JOptionPane.showInputDialog(frame,"Enter Book ID:");
            if(idStr == null || idStr.trim().isEmpty()) return;

            try{

                int id = Integer.parseInt(idStr);

                for(Book b : library.books){

                    if(b.id == id){

                        String info =
                                "ID: "+b.id+
                                        "\nTitle: "+b.title+
                                        "\nAuthor: "+b.author+
                                        "\nBorrowed: "+b.isBorrowed;

                        JOptionPane.showMessageDialog(frame,info);
                        return;

                    }

                }

                JOptionPane.showMessageDialog(frame,"Book not found");

            }catch(Exception ex){

                JOptionPane.showMessageDialog(frame,"Invalid ID");

            }

        });

        editBook.addActionListener(e -> {

            String idStr = JOptionPane.showInputDialog(frame,"Enter Book ID to Edit:");
            if(idStr == null || idStr.trim().isEmpty()) return;

            try{

                int id = Integer.parseInt(idStr);

                String newTitle = JOptionPane.showInputDialog(frame,"Enter New Title:");
                if(newTitle == null || newTitle.trim().isEmpty()) return;

                String newAuthor = JOptionPane.showInputDialog(frame,"Enter New Author:");
                if(newAuthor == null || newAuthor.trim().isEmpty()) return;

                library.editBook(id,newTitle,newAuthor);

            }catch(Exception ex){

                JOptionPane.showMessageDialog(frame,"Invalid ID");

            }

        });

        deleteBook.addActionListener(e -> {

            String idStr = JOptionPane.showInputDialog(frame,"Enter Book ID to Delete:");
            if(idStr == null || idStr.trim().isEmpty()) return;

            try{

                int id = Integer.parseInt(idStr);

                int confirm = JOptionPane.showConfirmDialog(
                        frame,
                        "Are you sure you want to delete this book?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

                if(confirm == JOptionPane.YES_OPTION){

                    library.deleteBook(id);

                    updateStats(totalBooks,availableBooks,borrowedBooks);

                }

            }catch(Exception ex){

                JOptionPane.showMessageDialog(frame,"Invalid ID");

            }

        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    static void updateStats(JLabel total,JLabel available,JLabel borrowed){

        int totalBooks = library.books.size();
        int borrowedCount = 0;

        for(Book b : library.books){

            if(b.isBorrowed) borrowedCount++;

        }

        int availableCount = totalBooks - borrowedCount;

        total.setText("Total: "+totalBooks);
        available.setText("Available: "+availableCount);
        borrowed.setText("Borrowed: "+borrowedCount);

    }

}