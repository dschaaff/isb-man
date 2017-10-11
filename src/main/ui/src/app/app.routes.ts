import {Routes, RouterModule} from "@angular/router";
import {ServersPage} from "./pages/servers/servers.page";
import {MetricsPage} from "./pages/metrics/metrics.page";

const routes: Routes = [
  { path: 'ui/servers', component: ServersPage },
  { path: 'ui/metrics', component: MetricsPage },
  { path: '', redirectTo: '/ui/servers', pathMatch: 'prefix'}
]

export const routing = RouterModule.forRoot(routes);
