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
        //В папке csv лежат два csv-файла: список книг и список авторов. В папке images лежат обложки книг.
        // В файле с книгами есть поле author_id и image_path. Первое поле обозначает идентификатор автора,
        // второе – название файла картинки(которая лежит в папке images).
    //• Создайте Map<Integer, String> authors. Ключ – id автора, значение – его имя.
    //• Прочитайте весь файл author.csv, записав каждую пару id-name в наш Map authors.

        List<Book> booksList = new ArrayList<>();

        Map <Integer, String> author = new HashMap<>();
        File authorFile = new File("csv/author.csv");
        File booksFile = new File("csv/book.csv");
        FileReader fileReader = null;

        //Не хочет автоматом создавать папку result, пришлось создавать отдельно.
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
        System.out.println("Создан обьект Map , название author  со всеми авторами наших книг.");

        String s = "";
        try(BufferedReader bReader = new BufferedReader(new FileReader(booksFile))){

            while((s=bReader.readLine())!=null) {
                String[] sArray = s.split(",");
                //  1,   UK для начинающих,   446.0,  69,   2145620.jpg,   1
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
        System.out.println("Создана коллекция обьектов наших книг с полями id, title, price, amount, image_path,  author_id ");
        //System.out.println(booksList.get(100));
        //System.out.println(author);

        //Итого у нас есть HashMap author с автором и его ID
        // и ArrayList  booksList  с обьектами типа Book  //  1,   UK для начинающих,   446.0,  69,   2145620.jpg,   1

        /*
        •	Прочитайте каждую строчку файла book.csv и для каждой строки сделайте следующее:
o	Считайте название книги (столбец title)
o	Считайте поле author_id
o	Из Map authors найдите имя автора по этому author_id
o	Считайте название файла-обложки книги (поле image_path)
o	Считайте картинку с этим названием (image_path), которая лежит в папке images в массив byte[]
o	Запишите этот массив байт в новую картинку. Картинка должна лежать в папке result/img.
Картинка должна иметь название вида «Имя автора – название книги». Например для первой строки из файла books.csv
картинка будет иметь название «Фрир О. – UK для начинающих».
        */
        // В конечном итоге нам нужна строка, с названием файла, которую мы будет использовать при создании файла.

        //Сказано прочитать, значит будем читать. Одним проходом читаем файл построчно, запоминаем нужную информацию и
        //создаем новый файл.
        String s2 = "";
        try(BufferedReader bReader = new BufferedReader(new FileReader(booksFile))){
            //Прочитайте каждую строчку файла book.csv и для каждой строки сделайте следующее:
            while((s2=bReader.readLine())!=null) {
                String[] sArray = s2.split(",");
                //  1,   UK для начинающих,   446.0,  69,   2145620.jpg,   1
                //*****Считайте название книги (столбец title)
                String title = sArray[1];
                //****Считайте поле author_id
                //int authorID = parseInt(sArray[5]);
                //Из Map authors найдите имя автора по этому author_id
                //String authorName = author.get(authorID);
                String authorName = author.get(parseInt(sArray[5]));
                //Считайте название файла-обложки книги (поле image_path)
                //o	Считайте картинку с этим названием (image_path), которая лежит в папке images в массив byte[]
                String imageName = "images"+ File.separator +sArray[4];  //потому что имя файла должно быть images\11111.jpg
                //File image = new File(imageName);
                InputStream inputStream = null;
                OutputStream outputStream = null;
                byte[] buffer = null;
                //File image = new File(sArray[4]);  //создаем обьект файл с именем нашей картинки
                try{
                    //inputStream = new FileInputStream(new File(sArray[4]));
                    inputStream = new FileInputStream(imageName);
                    buffer = new byte[inputStream.available()];
                    inputStream.read(buffer);                   //читаем наш файл с картинкой.
/*
                    Запишите этот массив байт в новую картинку. Картинка должна лежать в папке result/img.
                            Картинка должна иметь название вида «Имя автора – название книги». Например для первой строки из файла books.csv
                    картинка будет иметь название «Фрир О. – UK для начинающих».
                    //у нас есть String title и String authorName .
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

        //1.	Найдите самую дорогую книгу.  (поле price отвечает за цену)
        //2.	Найдите самую дешевую книгу.
        // у нас есть List<Book> booksList с полями id, title, price, amount, image_path,  author_id
        float bigPrice = 0;   //Почему float ?  в начале написал float, и так и поехало.
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
        //3.	Запишите в текстовый файл результаты. Файл должен лежать в папке result
        try(FileWriter fWriter = new FileWriter(new File("result/result.txt"))){
            fWriter.write("\nСамая дорогая книга: "+booksList.get(index).getTitleBook() + " , цена "+booksList.get(index).getPriceBook());
            fWriter.write("\nСамая дешевая книга: "+booksList.get(indexSmallPrice).getTitleBook() + " , цена "+booksList.get(indexSmallPrice).getPriceBook());
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }



        System.out.println();
        System.out.println("Самая дорогая книга: "+booksList.get(index).getTitleBook() + " , цена "+booksList.get(index).getPriceBook());
        System.out.println("Самая дешевая книга: "+booksList.get(indexSmallPrice).getTitleBook() + " , цена "+booksList.get(indexSmallPrice).getPriceBook());
    }
}