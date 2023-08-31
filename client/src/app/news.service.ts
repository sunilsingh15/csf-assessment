import { HttpClient } from '@angular/common/http';
import { ElementRef, Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NewsService {

  constructor(private http: HttpClient) { }

  postForm(form: FormGroup, photo: ElementRef): Observable<any> {

    const formData = new FormData();
    formData.set('title', form.value['title']);
    formData.set('description', form.value['description']);
    formData.set('image', photo.nativeElement.files[0]);

    if (form.value['tags'] !== '') {
      formData.set('tags', form.value['tags']);
    }

    return this.http.post('/api/post', formData);
  }

  getTopTags(minutes: number): Observable<any> {
    return this.http.get(`/api/posts/${minutes}`);
  }
}
