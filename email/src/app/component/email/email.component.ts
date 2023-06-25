import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { EmailService } from 'src/app/service/email.service';

@Component({
  selector: 'app-email',
  templateUrl: './email.component.html',
  styleUrls: ['./email.component.css'],
})
export class EmailComponent implements OnInit {
  data = {
    to: '',
    subject: '',
    message: '',
  };

  file!: File;

  flag = false;

  constructor(private email: EmailService, private snak: MatSnackBar) {}

  ngOnInit(): void {}

  onChange(e: any) {
    this.file = e.target.files[0];
  }

  doSubmitForm() {
    const fomdata = new FormData();
    if (this.file == null) {
      fomdata.append('to', this.data.to);
      fomdata.append('subject', this.data.subject);
      fomdata.append('message', this.data.message);
    } else {
      fomdata.append('file', this.file, this.file.name);
      fomdata.append('to', this.data.to);
      fomdata.append('subject', this.data.subject);
      fomdata.append('message', this.data.message);
    }

    console.log(this.data);

    if (
      this.data.to == '' ||
      this.data.subject == '' ||
      this.data.message == ''
    ) {
      this.snak.open('fields can not be empty', 'ok');
      return;
    }

    console.log(this.data);

    this.flag = true;

    this.email.sendEmail(fomdata).subscribe(
      (response) => {
        console.log(response);
        this.flag = false;
        this.snak.open('send sucess', 'ok');
      },
      (error) => {
        console.log(error);
        this.flag = false;
        this.snak.open('error', error);
      }
    );
  }
}
