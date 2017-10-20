import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ServersPage} from "./views/servers/servers.page";

// Import Containers
import {
  FullLayoutComponent,
  SimpleLayoutComponent
} from './containers';
import {MetricsPage} from "./views/metrics/metrics.page";

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'ui/servers',
    pathMatch: 'full',
  },
  {
    path: '',
    component: FullLayoutComponent,
    data: {
      title: 'Home'
    },
    children: [
      {
        path: 'ui/servers',
        component: ServersPage,
        data: {
          title: 'Severs'
        }
      }, {
        path: 'ui/metrics',
        component: MetricsPage,
        data: {
          title: 'Metrics'
        }
      }
    ]
  }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
