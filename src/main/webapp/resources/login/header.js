class Header extends HTMLElement {
    constructor() {
      super();
    }
  
    connectedCallback() {
      this.innerHTML = `
        <header>
            <nav>
                <ul class="Container">
                    <div class="LogoHeader">
                      <ul>
                        <li> <img src="${this.getAttribute('logo')}" alt="logo"></img> </li>
                        <li class="BrewDay">BrewDay!</li>
                      </ul>
                    </div>
                    <form class="Button" action="${this.getAttribute('submitTo')}" method="GET">
 	                    <input type="submit" value="${this.getAttribute('value')}"/>
 	                  </form>
                </ul>
            </nav>
        </header>
      `;
    }
  }
  
  customElements.define('header-accedi', Header);