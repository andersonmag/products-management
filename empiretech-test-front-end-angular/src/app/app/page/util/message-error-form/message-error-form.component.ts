import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-message-error-form',
  templateUrl: './message-error-form.component.html'
})
export class MessageErrorFormComponent {
  @Input() message!: string
}
