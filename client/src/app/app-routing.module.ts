import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { PostnewsComponent } from './components/postnews/postnews.component';
import { NewslistComponent } from './components/newslist/newslist.component';

const routes: Routes = [
  {path: '', component: HomeComponent, title: 'My News Website'},
  {path: 'post', component: PostnewsComponent, title: 'Post News'},
  {path: 'posts/:tag/:duration', component: NewslistComponent},
  {path: '**', redirectTo: ''}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
