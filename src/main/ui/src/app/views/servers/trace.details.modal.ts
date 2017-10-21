import { Component } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal/modal-options.class';

@Component({
  template: `
    <div class="modal-header">
      <h4 class="modal-title pull-left">{{trace?.info.path}} Trace</h4>
      <button type="button" class="close pull-right" aria-label="Close" (click)="bsModalRef.hide()">
        <span aria-hidden="true">&times;</span>
      </button>
    </div>
    <div class="modal-body">
      <h4>Info</h4>
      <table class="table table-striped table-hover table-condensed">
        <tbody>
        <tr><td width="20%"><b>timestamp</b></td><td>{{trace?.timestamp | date : 'medium'}}</td></tr>
        <tr><td><b>method</b></td><td>{{trace?.info.method}}</td></tr>
        <tr><td><b>path</b></td><td>{{trace?.info.path}}</td></tr>
        </tbody>
      </table>
      <br/>
      <h4>Request</h4>
      <table class="table table-striped table-hover table-condensed">
        <tbody>
        <tr *ngFor="let key of requestKeys()">
          <td width="20%"><b>{{key}}</b>
          <td>{{trace?.info.headers.request[key]}}</td>
        </tr>
        </tbody>
      </table>
      <br/>
      <h4>Response</h4>
      <table class="table table-striped table-hover table-condensed">
        <tbody>
        <tr *ngFor="let key of responseKeys()">
          <td width="20%"><b>{{key}}</b>
          <td>{{trace?.info.headers.response[key]}}</td>
        </tr>
        </tbody>
      </table>
    </div>
    <div class="modal-footer">
      <button type="button" class="btn btn-default" (click)="bsModalRef.hide()">Close</button>
    </div>  `
})
export class TraceDetailsModal {
  public trace;

  constructor(public bsModalRef: BsModalRef) {}

  requestKeys() {
    if (typeof this.trace == "undefined") {
      return [];
    }
    return Object.keys(this.trace.info.headers.request);
  }

  responseKeys():string[] {
    if (typeof this.trace == "undefined") {
      return [];
    }
    return Object.keys(this.trace.info.headers.response);
  }

}
