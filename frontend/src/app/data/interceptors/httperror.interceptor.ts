import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import {catchError, retry} from 'rxjs/operators';
import {AlertService} from "../../components/_alert";
import {Injectable} from "@angular/core";

@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {

  options = {
    autoClose: false,
    keepAfterRouteChange: false,
  };

  constructor(private alertService: AlertService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request)
      .pipe(
        retry(1),
        catchError((error: HttpErrorResponse) => {
          let errorMsg = '';
          if (error.error instanceof ErrorEvent) {
            console.log('this is client side error');
            errorMsg = `Error: ${error.error.message}`;
          }
          else {
            console.log('this is server side error');
            console.log(error)
            errorMsg = `Error Code: ${error.status},  Message: ${error.error.errors.join(", ")}`;
          }
          console.log(errorMsg);
          window.alert(errorMsg)
          //this.alertService.error(errorMsg, this.options);
          return throwError(errorMsg);
        })
      )
  }
}
