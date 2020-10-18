package rxjava;

public class OperatorMap<T,R> implements Oprator<R,T> {
    Func1<? super T,? extends R> transform;
    public OperatorMap(Func1<? super T,? extends R> transform){
        this.transform=transform;
    }



    @Override
    public Subscribe<? super T> call(Subscribe<? super R> subscribe) {
        return new MapSubscribe<>(subscribe,transform);
    }
    private class MapSubscribe<T,R> extends Subscribe<T>{

        private Subscribe<? super R> actual;
        Func1<? super T,? extends R> transform;
        public MapSubscribe(Subscribe<? super R> subscribe,Func1<? super T,? extends R> transform){
            this.actual=subscribe;
            this.transform=transform;
        }
        @Override
        public void onNext(T t) {
                R r = transform.call(t);
        }
    }
}
