package rxjava;

public class Observable<T> {

    public OnSubscribe<T> onSubscribe;

    public Observable(OnSubscribe<T> onSubscribe){
        this.onSubscribe=onSubscribe;
    }
    public static  <T> Observable<T> create(OnSubscribe<T> onSubscribe){

        return  new Observable<T>(onSubscribe);
    }
    public void subscribe(Subscribe<? super T> subscribe){
        onSubscribe.call(subscribe);
    }



}
