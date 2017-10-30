import { Component, ViewContainerRef } from '@angular/core';
import {StateService} from "../../services/state.service";
import {ServerService} from "../../services/server.service";
import { ToastsManager } from 'ng2-toastr/ng2-toastr';
import { BsModalRef } from 'ngx-bootstrap/modal/modal-options.class';
import { BsModalService } from 'ngx-bootstrap/modal';
import {EnvModal} from "./env.modal";
import {EnvsUtils} from "../../utils/envs.utils";
import {TraceModal} from "./trace.modal";
import {ThreadsModal} from "./threads.modal";

@Component({
  templateUrl: './servers.page.html'
})
export class ServersPage {
  public filterVisible=false;
  modal: BsModalRef;

  constructor(public state: StateService, public server: ServerService,
              private toastr: ToastsManager, vcr: ViewContainerRef, private bsModalRef: BsModalService) {
    this.toastr.setRootViewContainerRef(vcr);

  }

  toggleFilter() {
    this.filterVisible = !this.filterVisible;
  }


  copySSH(server) {
    this.toastr.success("Copied to clipboard", "ssh "+server.address);
    return false;
  }

  showEnv(selectedServer) {
    this.server.getEnv(selectedServer).subscribe( envs => {
      this.modal.content.envs = EnvsUtils.toArray(envs);
    });

    this.modal =this.bsModalRef.show(EnvModal);
    this.modal.content.server = selectedServer;
    return false;
  }

  showTrace(selectedServer) {
    this.server.getTrace(selectedServer).subscribe( trace => {
      this.modal.content.trace = trace;
    });

    this.modal =this.bsModalRef.show(TraceModal);
    this.modal.content.server = selectedServer;
    return false;
  }

  showThreads(selectedServer) {
    this.modal =this.bsModalRef.show(ThreadsModal);
    this.modal.content.server = selectedServer;
    this.server.getThreads(selectedServer).subscribe( threads => {
      this.modal.content.threads = threads;
    });
    return false;
  }



}
