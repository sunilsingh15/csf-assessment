import { Component, ElementRef, OnInit, ViewChild, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { NewsService } from 'src/app/news.service';

@Component({
  selector: 'app-postnews',
  templateUrl: './postnews.component.html',
  styleUrls: ['./postnews.component.css']
})
export class PostnewsComponent implements OnInit {

  builder = inject(FormBuilder);
  service = inject(NewsService);
  router = inject(Router);
  sub$!: Subscription;

  @ViewChild('photo')
  photo!: ElementRef;

  postForm!: FormGroup;
  tags: string[] = [];

  ngOnInit(): void {
    this.postForm = this.initializeForm();
  }

  initializeForm(): FormGroup {
    return this.builder.group({
      title: this.builder.control<string>('', [Validators.required, Validators.minLength(5)]),
      photo: this.builder.control('', Validators.required),
      description: this.builder.control<string>('', [Validators.required, Validators.minLength(5)]),
      tags: this.builder.control<string>('')
    });
  }

  addTags(enteredTags: string) {
    this.tags = enteredTags.split(' ');
  }

  removeTag(selectedTag: string) {
    let idx = this.tags.indexOf(selectedTag, 0);
    this.tags.splice(idx, 1);
  }

  processForm() {
    this.sub$ = this.service.postForm(this.postForm, this.photo).subscribe({
      next: (result) => {alert('Your news has been posted successfully, with id: ' + result.newsId);
                         this.router.navigate(['/'])},
      error: (err) => {console.log(err)},
      complete: () => {this.sub$.unsubscribe()}
    });

  }

}
