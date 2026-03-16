package library;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class Library {

    public ArrayList<Book> books = new ArrayList<>();

    String fileName = "books.dat";

    public void addBook(Book book){

        books.add(book);
        saveBooks();

    }

    public void borrowBook(int id){

        for(Book b : books){

            if(b.id == id){

                if(!b.isBorrowed){

                    b.isBorrowed = true;
                    JOptionPane.showMessageDialog(null,"Book Borrowed");
                    saveBooks();

                }else{

                    JOptionPane.showMessageDialog(null,"Book already borrowed");

                }

                return;

            }

        }

        JOptionPane.showMessageDialog(null,"Book not found");

    }

    public void returnBook(int id){

        for(Book b : books){

            if(b.id == id){

                if(b.isBorrowed){

                    b.isBorrowed = false;
                    JOptionPane.showMessageDialog(null,"Book Returned");
                    saveBooks();

                }else{

                    JOptionPane.showMessageDialog(null,"Book was not borrowed");

                }

                return;

            }

        }

        JOptionPane.showMessageDialog(null,"Book not found");

    }

    public void editBook(int id,String newTitle,String newAuthor){

        for(Book b : books){

            if(b.id == id){

                b.title = newTitle;
                b.author = newAuthor;

                saveBooks();

                JOptionPane.showMessageDialog(null,"Book Updated");
                return;

            }

        }

        JOptionPane.showMessageDialog(null,"Book not found");

    }

    public void deleteBook(int id){

        for(int i=0;i<books.size();i++){

            if(books.get(i).id == id){

                books.remove(i);
                saveBooks();

                JOptionPane.showMessageDialog(null,"Book Deleted");
                return;

            }

        }

        JOptionPane.showMessageDialog(null,"Book not found");

    }

    public void saveBooks(){

        try{

            ObjectOutputStream out =
                    new ObjectOutputStream(new FileOutputStream(fileName));

            out.writeObject(books);
            out.close();

        }catch(Exception e){

            e.printStackTrace();

        }

    }

    public void loadBooks(){

        try{

            ObjectInputStream in =
                    new ObjectInputStream(new FileInputStream(fileName));

            books = (ArrayList<Book>) in.readObject();
            in.close();

        }catch(Exception e){

            books = new ArrayList<>();

        }

    }

}