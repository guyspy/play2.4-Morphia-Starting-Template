package utils.rx;

import play.libs.F;
import java.util.*;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import rx.Observable;
import rx.Observer;
import rx.functions.Func1;


public class RxUtil<R> {

  /**
   * convert RxJava Observable back to play's F.Promise
   * @param  obs the Observable to convert
   * @return     F.Promise of the same type
   */
  public F.Promise<R> toPromise(Observable<R> obs){
    final scala.concurrent.Promise<R> scalaPromise = scala.concurrent.Promise$.MODULE$.<R> apply();
    obs.subscribe(new Observer<R>() {

      @Override
      public void onCompleted() {
        // Nothing to do here.
      }

      @Override
      public void onError(Throwable t) {
        if (t instanceof HystrixRuntimeException) {
          // This is a wrapper exception. Get cause here.
          Throwable t1 = t.getCause();
          if (t1 instanceof RuntimeException) {
            // This is the underlying exception.
            scalaPromise.failure((RuntimeException) t1);
          } else {
            scalaPromise.failure(t);
          }
        } else {
          scalaPromise.failure(t);
        }
      }

      @Override
      public void onNext(R r) {
        scalaPromise.success(r);
      }

    });
    return F.Promise.wrap(scalaPromise.future());
  }

}
