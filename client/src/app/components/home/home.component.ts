import { Component, ElementRef, OnInit, ViewChild, inject } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, Subscription } from 'rxjs';
import { NewsService } from 'src/app/news.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  service = inject(NewsService);
  sub$!: Subscription;
  topTags: any[] = [];

  @ViewChild('select')
  selectedVal!: ElementRef;

  timeValue!: number;

  ngOnInit(): void {
    this.timeValue = 5;
    this.getTopTags(this.timeValue);
  }

  onDropdownChange(val: string) {
    this.timeValue = Number(val);
    this.getTopTags(this.timeValue);
  }

  getTopTags(minutes: number) {
    this.topTags = [];
    this.sub$ = this.service.getTopTags(minutes).subscribe({
      next: (result) => {
        this.topTags = result;
      },
      error: (err) => { console.log(err) },
      complete: () => { this.sub$.unsubscribe() }
    });
  }

}
