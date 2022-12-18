import javax.imageio.ImageIO;
import java.awt.desktop.FilesEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;

public class Main {
    public static void main(String[] args) {
        //� ����� csv ����� ��� csv-�����: ������ ���� � ������ �������. � ����� images ����� ������� ����.
        // � ����� � ������� ���� ���� author_id � image_path. ������ ���� ���������� ������������� ������,
        // ������ � �������� ����� ��������(������� ����� � ����� images).
    //� �������� Map<Integer, String> authors. ���� � id ������, �������� � ��� ���.
    //� ���������� ���� ���� author.csv, ������� ������ ���� id-name � ��� Map authors.

        List<Book> booksList = new ArrayList<>();

        Map <Integer, String> author = new HashMap<>();
        File authorFile = new File("csv/author.csv");
        File booksFile = new File("csv/book.csv");
        FileReader fileReader = null;

        //�� ����� ��������� ��������� ����� result, �������� ��������� ��������.
        File dirResult = new File("result");
        dirResult.mkdir();

        Scanner scanner = new Scanner(System.in);
        try{scanner = new Scanner(authorFile);
        }
        catch(FileNotFoundException ex){
            System.out.println("File not found.");
        }

        while(scanner.hasNextLine()){
            String string = scanner.nextLine();
            String[] arrayString = string.split(",");
            //Integer id = Integer.parseInt(arrayString[0]);
            author.put(valueOf(arrayString[0]), arrayString[1]);
        }
        System.out.println("������ ������ Map , �������� author  �� ����� �������� ����� ����.");

        String s = "";
        try(BufferedReader bReader = new BufferedReader(new FileReader(booksFile))){

            while((s=bReader.readLine())!=null) {
                String[] sArray = s.split(",");
                //  1,   UK ��� ����������,   446.0,  69,   2145620.jpg,   1
                booksList.add(new Book(parseInt(sArray[0]),
                                        sArray[1],
                                        parseFloat(sArray[2]),
                                        parseInt(sArray[3]),
                                        sArray[4],
                                        parseInt(sArray[5])));
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        System.out.println("������� ��������� �������� ����� ���� � ������ id, title, price, amount, image_path,  author_id ");
        //System.out.println(booksList.get(100));
        //System.out.println(author);

        //����� � ��� ���� HashMap author � ������� � ��� ID
        // � ArrayList  booksList  � ��������� ���� Book  //  1,   UK ��� ����������,   446.0,  69,   2145620.jpg,   1

        /*
        �	���������� ������ ������� ����� book.csv � ��� ������ ������ �������� ���������:
o	�������� �������� ����� (������� title)
o	�������� ���� author_id
o	�� Map authors ������� ��� ������ �� ����� author_id
o	�������� �������� �����-������� ����� (���� image_path)
o	�������� �������� � ���� ��������� (image_path), ������� ����� � ����� images � ������ byte[]
o	�������� ���� ������ ���� � ����� ��������. �������� ������ ������ � ����� result/img.
�������� ������ ����� �������� ���� ���� ������ � �������� �����. �������� ��� ������ ������ �� ����� books.csv
�������� ����� ����� �������� ����� �. � UK ��� �����������.
        */
        // � �������� ����� ��� ����� ������, � ��������� �����, ������� �� ����� ������������ ��� �������� �����.

        //������� ���������, ������ ����� ������. ����� �������� ������ ���� ���������, ���������� ������ ���������� �
        //������� ����� ����.
        String s2 = "";
        try(BufferedReader bReader = new BufferedReader(new FileReader(booksFile))){
            //���������� ������ ������� ����� book.csv � ��� ������ ������ �������� ���������:
            while((s2=bReader.readLine())!=null) {
                String[] sArray = s2.split(",");
                //  1,   UK ��� ����������,   446.0,  69,   2145620.jpg,   1
                //*****�������� �������� ����� (������� title)
                String title = sArray[1];
                //****�������� ���� author_id
                //int authorID = parseInt(sArray[5]);
                //�� Map authors ������� ��� ������ �� ����� author_id
                //String authorName = author.get(authorID);
                String authorName = author.get(parseInt(sArray[5]));
                //�������� �������� �����-������� ����� (���� image_path)
                //o	�������� �������� � ���� ��������� (image_path), ������� ����� � ����� images � ������ byte[]
                String imageName = "images"+ File.separator +sArray[4];  //������ ��� ��� ����� ������ ���� images\11111.jpg
                //File image = new File(imageName);
                InputStream inputStream = null;
                OutputStream outputStream = null;
                byte[] buffer = null;
                //File image = new File(sArray[4]);  //������� ������ ���� � ������ ����� ��������
                try{
                    //inputStream = new FileInputStream(new File(sArray[4]));
                    inputStream = new FileInputStream(imageName);
                    buffer = new byte[inputStream.available()];
                    inputStream.read(buffer);                   //������ ��� ���� � ���������.
/*
                    �������� ���� ������ ���� � ����� ��������. �������� ������ ������ � ����� result/img.
                            �������� ������ ����� �������� ���� ���� ������ � �������� �����. �������� ��� ������ ������ �� ����� books.csv
                    �������� ����� ����� �������� ����� �. � UK ��� �����������.
                    //� ��� ���� String title � String authorName .
*/
                    outputStream = new FileOutputStream("result"+File.separator+authorName+" - "+title+".jpg");
                    outputStream.write(buffer);

                }
                catch(FileNotFoundException ex){
                    System.out.println(ex.getMessage());
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
                finally{
                    try{
                        inputStream.close();
                        //outputStream.close();
                    }
                    catch(IOException ex){
                        System.out.println(ex.getMessage());
                    }
                }
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }

        //1.	������� ����� ������� �����.  (���� price �������� �� ����)
        //2.	������� ����� ������� �����.
        // � ��� ���� List<Book> booksList � ������ id, title, price, amount, image_path,  author_id
        float bigPrice = 0;   //������ float ?  � ������ ������� float, � ��� � �������.
        float smallPrice = 0;
        int index = 0;
        int indexSmallPrice = 0;
        for(int i=0; i<booksList.size(); i++){
            if(booksList.get(i).getPriceBook() > bigPrice){
                bigPrice = booksList.get(i).getPriceBook();
                index= i;
            }
            if(smallPrice == 0){
                smallPrice = booksList.get(i).getPriceBook();
                indexSmallPrice = i;
            }
            if(booksList.get(i).getPriceBook() < smallPrice){
                smallPrice = booksList.get(i).getPriceBook();
                indexSmallPrice = i;
            }


        }
        System.out.println(bigPrice);
        System.out.println(smallPrice);
        //3.	�������� � ��������� ���� ����������. ���� ������ ������ � ����� result
        try(FileWriter fWriter = new FileWriter(new File("result/result.txt"))){
            fWriter.write("\n����� ������� �����: "+booksList.get(index).getTitleBook() + " , ���� "+booksList.get(index).getPriceBook());
            fWriter.write("\n����� ������� �����: "+booksList.get(indexSmallPrice).getTitleBook() + " , ���� "+booksList.get(indexSmallPrice).getPriceBook());
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }



        System.out.println();
        System.out.println("����� ������� �����: "+booksList.get(index).getTitleBook() + " , ���� "+booksList.get(index).getPriceBook());
        System.out.println("����� ������� �����: "+booksList.get(indexSmallPrice).getTitleBook() + " , ���� "+booksList.get(indexSmallPrice).getPriceBook());
    }
}