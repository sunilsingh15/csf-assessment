import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { NewsService } from 'src/app/news.service';

@Component({
  selector: 'app-newslist',
  templateUrl: './newslist.component.html',
  styleUrls: ['./newslist.component.css']
})
export class NewslistComponent implements OnInit {

  activatedRoute = inject(ActivatedRoute);
  service = inject(NewsService);
  sub$!: Subscription;

  hashtag: string = this.activatedRoute.snapshot.params['tag'];
  duration: number = this.activatedRoute.snapshot.params['duration'];

  posts: any[] = [];

  ngOnInit(): void {
    this.sub$ = this.service.getPosts(this.hashtag, this.duration).subscribe({
      next: (result) => { this.posts = result },
      error: (err) => { console.log(err); },
      complete: () => { this.sub$.unsubscribe() }
    });
  }

}
