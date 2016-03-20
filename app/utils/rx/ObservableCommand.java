package utils.rx;

import play.libs.F;
import java.util.*;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import rx.Observable;
import rx.Observer;
import rx.functions.Func1;

/**
 * wrap HystrixObservableCommand with an toPromise() method to convieniently convert Observable back to play's Promise
 */
public abstract class ObservableCommand<R> extends HystrixObservableCommand<R> {

  protected String groupKey;

  protected ObservableCommand(String groupKey){
    super(HystrixCommandGroupKey.Factory.asKey(groupKey));
    this.groupKey = groupKey;
  }

  protected abstract Observable<R> construct();

  public F.Promise<R> toPromise(){
    Observable<R> obs = this.observe();
    return new RxUtil<R>().toPromise(obs);
  }


}
