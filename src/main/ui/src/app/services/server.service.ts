import {Injectable} from "@angular/core";
import {Http} from '@angular/http';
import 'rxjs/add/operator/map';
import {Observable} from "rxjs/Rx";
import {StateService} from "./state.service";

@Injectable()
export class ServerService {

  constructor(private http: Http) {
  }

  getServersURL() : string { return "/api/servers"; }
  getServers(): Observable<any[]> {
    console.log("getServers: "+this.getServersURL());
    return this.http.get(this.getServersURL()).map(res => res.json());
  }

  getMetricsURL(state: StateService) : string {
    return "/api/metrics?env="+this.enc(state.env)+
      "&service="+this.enc(state.service)+
      "&hostname="+this.enc(state.hostname);
  }
  getMetrics(state: StateService) : Observable<any[]> {
    return this.http.get(this.getMetricsURL(state)).map(res => res.json());
  }

  getEnvUrl(server): string { return "/api/servers/" + server.address + '/' + server.managePort + '/env'; }
  getEnv(server): Observable<any> {
    return this.http.get(this.getEnvUrl(server)).map(res => res.json());
  }


  private enc(value) {
    if (value == null) {
      return '';
    }
    return encodeURIComponent(value);
  }

}
