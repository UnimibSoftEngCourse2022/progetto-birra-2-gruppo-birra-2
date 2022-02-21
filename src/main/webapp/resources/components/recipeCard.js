class Card extends HTMLElement {
    constructor() {
      super();
    }
  
    connectedCallback() {
      this.innerHTML = `
        <div class="Card" onclick="${this.getAttribute('elementClicked')}">
            <div class="HStack">
              <img src="${this.getAttribute('image')}" alt="card image"></img>
              <div class="Text">
                  <h3>${this.getAttribute('title')}</h3>
                  <p>${this.getAttribute('description')}</p>
              </div>
            </div>
        </div>
      `;
    }
  }
  
  customElements.define('recipe-card', Card);
  
  //USAGE:  <recipe-card title="" description="" elementClicked="" ></recipe-card>