import { Component } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal/modal-options.class';
import { BsModalService } from 'ngx-bootstrap/modal';
import {TraceDetailsModal} from "./trace.details.modal";
import {ServerService} from "../../services/server.service";

@Component({
  template: `
    <div class="modal-header">
      <h4 class="modal-title pull-left">{{server?.hostname}}</h4>
      <button type="button" class="close pull-right" aria-label="Close" (click)="modalService.hide(1)">
        <span aria-hidden="true">&times;</span>
      </button>
    </div>
    <div class="modal-body">
      <div *ngIf="server != null" class="pull-right"><a [href]="serverService.getTraceUrl(server)" target="source">Source</a></div>
      <dtable 
        [title]="'Trace'"
        [columns]="[
            { title: 'Timestamp' },
            { title: 'Path', filterable: true, property: 'info.path'},
            { title: 'Method', filterable: true, property: 'info.method'},
            { title: 'Status', filterable: true, property: 'info.headers.response.status'}
        ]"
        [data]="trace"
        [template]="rows"
      >
        <ng-template #rows let-t="row">
          <td>{{t.timestamp | date : 'medium'}}</td>
          <td><a (click)="openTraceDetails(t)" href="#">{{t.info.path}}</a></td>
          <td>{{t.info.method}}</td>
          <td>{{t.info.headers.response.status}}</td>
        </ng-template>
      </dtable>
    </div>
    <div class="modal-footer">
      <button type="button" class="btn btn-default" (click)="modalService.hide(1)">Close</button>
    </div>  `
})
export class TraceModal {
  public server = null;
  public trace = [];
  modal;

  constructor( public modalService: BsModalService, public serverService: ServerService) {}

  openTraceDetails(trace) {
    console.log(JSON.stringify(trace.info));
    this.modal = this.modalService.show(TraceDetailsModal, {class: 'modal-lg'});
    this.modal.content.trace = trace;
    return false;
  }

}
