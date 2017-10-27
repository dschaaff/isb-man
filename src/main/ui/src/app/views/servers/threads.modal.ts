import { Component } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal/modal-options.class';
import { BsModalService } from 'ngx-bootstrap/modal';
import {TraceDetailsModal} from "./trace.details.modal";
import {ThreadDetailsModal} from "./thread.details.modal";

@Component({
  template: `
    <div class="modal-header">
      <h4 class="modal-title pull-left">{{server?.hostname}}</h4>
      <button type="button" class="close pull-right" aria-label="Close" (click)="modal.hide()">
        <span aria-hidden="true">&times;</span>
      </button>
    </div>
    <div class="modal-body">
      <dtable 
        [title]="'Trace'"
        [columns]="[
            { title: 'ID', filterable: true, property: 'threadId' },
            { title: 'Name', filterable: true, property: 'threadName'},
            { title: 'State', filterable: true, property: 'threadState'},
            { title: 'Waited Time', filterable: true, property: 'waitedTime'},
            { title: 'Blocked Time', filterable: true, property: 'blockedTime'},
            { title: 'Native', filterable: true, property: 'inNative'},
            { title: 'Suspended', filterable: true, property: 'suspended'}
        ]"
        [data]="threads"
        [template]="rows"
      >
        <ng-template #rows let-thread="row">
          <td>{{thread.threadId}}</td>
          <td><a href="#" (click)="displayThread(thread)">{{thread.threadName}}</a></td>
          <td>{{thread.threadState}}</td>
          <td>{{thread.waitedTime}}</td>
          <td>{{thread.blockedTime}}</td>
          <td>{{thread.inNative}}</td>
          <td>{{thread.suspended}}</td>
        </ng-template>
      </dtable>
    </div>
    <div class="modal-footer">
      <button type="button" class="btn btn-default" (click)="modal.hide()">Close</button>
    </div>  `
})
export class ThreadsModal {
  public server;
  public threads = [];
  modal;

  constructor( private modalService: BsModalService) {}

  displayThread(thread) {
    this.modal = this.modalService.show(ThreadDetailsModal, {class: 'modal-lg'});
    this.modal.content.thread = thread;
  }

}
