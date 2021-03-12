import { Injectable } from '@angular/core';
import {Subject} from "rxjs";

declare const window: any;

@Injectable({
  providedIn: 'root'
})
export class OnlineOfflineService {

  private internalConnectionChanged = new Subject<boolean>();

  constructor() {
    window.addEventListener('online', () => this.updateOnlineStatus());
    window.addEventListener('offline', () => this.updateOnlineStatus());
  }

  private updateOnlineStatus() {
    this.internalConnectionChanged.next(window.navigator.onLine);
  }

  get connectionChanged() {
    return this.internalConnectionChanged.asObservable();
  }

  get isOnline() {
    return !!window.navigator.onLine;
  }
}
