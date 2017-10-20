import {Component, Input} from "@angular/core";

@Component({
  selector: 'gauges-table',
  templateUrl: './gauges.table.html'
})
export class GaugesTable {
  @Input() gauges: any[] = [];
}
