import { Component } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal/modal-options.class';

@Component({
  template: `
    <div class="modal-header">
      <h4 class="modal-title pull-left">{{server.hostname}}</h4>
      <button type="button" class="close pull-right" aria-label="Close" (click)="bsModalRef.hide()">
        <span aria-hidden="true">&times;</span>
      </button>
    </div>
    <div class="modal-body">
      <dtable 
        [title]="'Environment'"
        [columns]="[
            { title: 'Source', filterable: true, property: 'source' },
            { title: 'Name', filterable: true, property: 'name'},
            { title: 'Value', filterable: true, property: 'value'}
        ]"
        [data]="envs"
        [template]="rows"
      >
        <ng-template #rows let-prop="row">
          <td>{{prop.source}}</td>
          <td>{{prop.name}}</td>
          <td>{{prop.value}}</td>
        </ng-template>
      </dtable>
    </div>
    <div class="modal-footer">
      <button type="button" class="btn btn-default" (click)="bsModalRef.hide()">Close</button>
    </div>  `
})
export class EnvModal {
  public server = {hostname:""};
  public envs = [];

  constructor(public bsModalRef: BsModalRef) {}

}
