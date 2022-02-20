class Logo extends HTMLElement {
    constructor() {
      super();
    }
  
    connectedCallback() {
      this.innerHTML = `
            <style>

            .LogoHeader p {
                font-family: 'Sora', sans-serif;
                font-weight: 700;
                color: #FFCC00;
                font-size: 30px;
                letter-spacing: -0.04em;
              }
              
              .LogoHeader img {
                height: 32px;
                width: 32px;
              }
              
              div.LogoHeader {
                display: flex;
                flex-direction: row;
                align-items: center;
                align-content: center;
                height: max-content;
                width: max-content;
                gap: 13px;
              }

            </style>

            <div class="LogoHeader">
                <img src="${this.getAttribute('logo')}" alt="logo"></img>
                <p>BrewDay!</p>
            </div>
      `;
    }
  }
  
  customElements.define('header-logo', Logo);
  