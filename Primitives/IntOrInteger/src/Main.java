public class Main {
    public static void main(String[] args) {
        Container container = new Container();
        container.addCount(5672);
        System.out.println(container.getCount());

        // TODO: ниже напишите код для выполнения задания:
        //  С помощью цикла и преобразования чисел в символы найдите все коды
        //  букв русского алфавита — заглавных и строчных, в том числе буквы Ё.

        for (char i = 'А'; i <= 'Е'; i++) {
            if ( i >= 'А' &&  i <= 'Е');
            System.out.println( (int) i + " - " + i);
        }
        System.out.println( (int) 'Ё' + " - " + 'Ё');
        for (char i = 'Ж'; i <= 'е'; i++) {
            if ( i >= 'Ж' &&  i <= 'е');
            System.out.println( (int) i + " - " + i);
        }
        System.out.println( (int) 'ё' + " - " + 'ё');
        for (char i = 'ж'; i <= 'я'; i++) {
            if ( i >= 'ж' &&  i <= 'я');
            System.out.println( (int) i + " - " + i);
        }
    }
}
