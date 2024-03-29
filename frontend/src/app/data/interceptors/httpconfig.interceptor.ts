import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {map} from "rxjs/operators";
import {Observable} from "rxjs";

@Injectable()
export class HttpConfigInterceptor implements HttpInterceptor {
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token: string | null = localStorage.getItem('token');

    if (token) {
      request = request.clone({ headers: request.headers.set('Authorization', 'Bearer ' + token) });
    }

    if (!request.headers.has('Content-Type')) {
      request = request.clone({ headers: request.headers.set('Content-Type', 'application/json') });
    }

    request = request.clone({ headers: request.headers.set('Accept', 'application/json') });
    request = request.clone({ headers: request.headers.set('Access-Control-Allow-Origin', '*') });
    request = request.clone({ headers: request.headers.set('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, OPTIONS') });
    request = request.clone({ headers: request.headers.set('Access-Control-Allow-Headers', '*') });

    return next.handle(request).pipe(
      map((event: HttpEvent<any>) => {
        if (event instanceof HttpResponse) {
          console.log('event--->>>', event);
        }
        return event;
      }));
  }
}
