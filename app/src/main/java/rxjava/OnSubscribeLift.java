package rxjava;

public class OnSubscribeLift<T,R> implements OnSubscribe<R>  {
    OnSubscribe<T> parent;

    @Override
    public void call(Subscribe<? super R> subscribe) {

    }
}
