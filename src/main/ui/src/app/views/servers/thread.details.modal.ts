import { Component } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal/modal-options.class';

@Component({
  template: `
    <div class="modal-header">
      <h4 class="modal-title pull-left">{{thread?.threadName}} Thread</h4>
      <button type="button" class="close pull-right" aria-label="Close" (click)="bsModalRef.hide()">
        <span aria-hidden="true">&times;</span>
      </button>
    </div>
    <div class="modal-body">
      <div *ngIf="thread?.lockInfo">
        <h4>Info</h4>
        <table class="table table-striped table-hover table-condensed">
          <tbody>
          <tr>
            <td>Class</td><td>{{thread?.lockInfo.className}}</td>
          </tr>
          <tr>
            <td>Identity</td><td>{{thread?.lockInfo.identityHashCode}}</td>
          </tr>
          </tbody>
        </table>
      </div>
      <h4>StackTrace</h4>
      <table class="table table-striped table-hover table-condensed">
        <thead>
        <th>File</th>
        <th>Class</th>
        <th>Method</th>
        <th>Line</th>
        <th>Native</th>
        </thead>
        <tbody>
        <tr *ngFor="let line of thread?.stackTrace">
          <td>{{line.fileName}}</td>
          <td>{{line.className}}</td>
          <td>{{line.methodName}}</td>
          <td>{{line.lineNumber}}</td>
          <td>{{line.nativeMethod}}</td>
        </tr>
        </tbody>
      </table>
    </div>
    <div class="modal-footer">
      <button type="button" class="btn btn-default" (click)="bsModalRef.hide()">Close</button>
    </div>  `
})
export class ThreadDetailsModal {
  public thread;
  constructor(public bsModalRef: BsModalRef) {}
}
