
import {Component, Input} from "@angular/core";
import {StateService} from "../../services/state.service";

@Component({
  selector: 'metrics-tab',
  templateUrl: './metrics.tab.html'
})
export class MetricsTab {
  @Input() metrics:any = {timers: [], gauges: [], counters: [], meters: []};

  constructor(private state:StateService) {
  }

}
