public class Arithmetic {
        int a = 3 ;
        int b = 8 ;

        public Arithmetic (){
            this.a = a;
            this.b = b;
        }

        public int sum() {
            return a + b;
        }
        public int composition() {
            return a * b;
        }
        public int max() {
            return a > b ? a : b;
        }
        public int min() {
            return  a < b ? a : b;
        }
}
