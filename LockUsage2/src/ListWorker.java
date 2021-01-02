import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
public class ListWorker {
    
    Random random = new Random();
    ArrayList<Integer> array1 = new ArrayList<Integer>();
    ArrayList<Integer> array2 = new ArrayList<Integer>();
    public synchronized void degerEkleListe1(){
        try {
            Thread.sleep(1);
        } catch (InterruptedException ex) {
            Logger.getLogger(ListWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
            array1.add(random.nextInt(100));

    }

    public synchronized void degerEkleListe2(){
        try {
            Thread.sleep(1);
        } catch (InterruptedException ex) {
            Logger.getLogger(ListWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
            array2.add(random.nextInt(100));//standart bir değer atama yapmıyoruz lockusage projesinde olduğu gibi sıralı değer atmıyoruz.Sıra ile random değerler atıyoruz.
           
    }
    public void degerAta(){//2000 değer atamamız için bu metodu 2 trhreadin çağırması gerekiyor
        for(int i = 0 ;i<1000;i++){
        degerEkleListe1();
        degerEkleListe2();
        }
       
        //System.out.println("array1 size = "+array1.size()+" array2 size = "+array2.size());
    }
    //Threadlerimi oluşturuyorum = 
    public void calıstır(){
        //2 adet thread oluşturuyoruz .
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
              degerAta();//1 çağırma 1 thread
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
               degerAta();// 1 cağıgrma 1 thread
               //Hangisinin önce cağrılacağı işletim sistemi ve JVM'ye bağlıdır.
            }
        });
        //zamanı ölçmek için = 
        long baslangıc = System.currentTimeMillis();
        
        t1.start();
        t2.start();
        try {
            //Threadlerin bitmesini beklemek istiyoruz bu sebeple join() me-todunu kullanıyoruz.
            t1.join();
            t2.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(ListWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Array1 size = "+array1.size()+" array2 size = "+array2.size());
        long bitis = System.currentTimeMillis();
        
        System.out.println("İşlemler Süresince Geçen Süre : "+(bitis-baslangıc)+" ms.");
        //Geçen süre yarıya indi diyebiliriz 2 thread süreyi yarıya indirmemizi sağladı ama 2000 değer atama konusunda başarı istatistiklerimiz düşük
        //Synchronized ile bunu düzeltebiliyoruz.Bu sebeple süre yine eski seviyelerine gelmiş oldu.Bu sorunun temel sebebi anahtaqr bir odadan çıkmadan diğerine giremez olarak düşündüğümüz anahtarın metoda değil classa ait oluşu ve bir adet oluşudur.
        //bizim burdaki sorunu çözmek içn 2 anahtarla yapıyı kurmamız gerekiyor.
        //LockUsage3 projesine gidiniz
    }
    
}

