import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ServersPage} from "./views/servers/servers.page";
import {MetricsPage} from "./views/metrics/metrics.page";
import {DatasourcesPage} from "./views/datasources/datasources.page";

export const routes: Routes = [
  { path: 'ui/servers', component: ServersPage},
  { path: 'ui/metrics', component: MetricsPage},
  { path: "ui/datasources", component: DatasourcesPage},
  { path: '', redirectTo: "/ui/servers", pathMatch: "prefix"}
];

export const routing = RouterModule.forRoot(routes);
