
import {Injectable, Output, EventEmitter} from "@angular/core";
import {ServerService} from "./server.service";
import {CollectionUtils} from "../utils/collection.utils";

@Injectable()
export class StateService {
  @Output() change = new EventEmitter();
  env: string = null;
  service: string = null;
  hostname: string = null;

  envs: string[] = [];
  services: string[] = [];
  hostnames: string[] = [];

  _servers: any[] = [];
  servers: any[] = [];

  constructor(private server: ServerService) {
    this.server.getServers().subscribe(
      servers => {
        this.setServers(servers);
      }
    );
  }

  setEnv(env: string) {
    this.env = env;
    this.serversUpdate();
  }

  setService(service: string) {
    this.service = service;
    this.serversUpdate();
  }

  setHostname(hostname: string) {
    this.hostname = hostname;
    this.serversUpdate();
  }

  private setServers(servers: any[]) {
    this._servers = servers;
    this.serversUpdate();
  }

  private serversUpdate() {
    this.updateToolbar();
    this.filterServers();
    this.change.emit(null);
  }

  private updateToolbar() {
    this.envs = CollectionUtils.toSet(this._servers,'environment');
    if (this.env == null) {
      this.env = this.envs[0];
    }

    let serversInEnv = [];
    for (let server of this._servers) {
      if (server.environment === this.env) {
        serversInEnv.push(server);
      }
    }
    this.services = CollectionUtils.toSet(serversInEnv,"serviceType");
    if (CollectionUtils.posInArray(this.service,this.services) === -1) {
      this.service = null;
    }

    let serversInService = [];
    for (let server of serversInEnv) {
      if (this.service == null || server.serviceType == this.service) {
        serversInService.push(server);
      }
    }
    this.hostnames = CollectionUtils.toSet(serversInService,"hostname");
    if (CollectionUtils.posInArray(this.hostname,this.hostnames) === -1) {
      this.hostname == null;
    }

  }

  private filterServers() {
    this.servers = [];
    for (let server of this._servers) {
      if (server.environment !== this.env) {
        continue;
      }
      if (this.service !== null) {
        if (server.serviceType !== this.service) {
          continue;
        }
      }
      if (this.hostname !== null) {
        if (server.hostname !== this.hostname) {
          continue;
        }
      }
      this.servers.push(server);
    }
  }

}
