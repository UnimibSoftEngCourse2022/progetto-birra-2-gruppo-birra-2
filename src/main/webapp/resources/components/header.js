class Header extends HTMLElement {
    constructor() {
      super();
    }
  
    connectedCallback() {
      this.innerHTML = `
          <header class="site">
              <nav>
                  <ul class="Container">
                      <div class="LogoHeader">
                          <ul>
                              <li> <img src="${this.getAttribute('logo')}" alt="logo"></img> </li>
                              <li class="BrewDay">BrewDay!</li>
                          </ul>
                      </div>
  
                      <div class="Pagine">
                      <ul>
                          <li>
                              <form action="homePage" method="GET">
                                  <input id="home" type="submit" value="Home"/>
                              </form>
                          </li>
                          <li>
                              <form action="birrePage" method="GET">
                                  <input id="birre" type="submit" value="Birre"/>
                              </form>
                          </li>
                          <li>
                              <form action="recipes" method="GET">
                                  <input id="ricette" type="submit" value="Ricette"/>
                              </form>
                          </li>
                          <li>
                              <input id="ingredienti" type="submit" value="Ingredienti"
                              onclick="${this.getAttribute('ingredients')}"/>
                              &nbsp;&nbsp;&nbsp;
                          <li>
                              <input id="attrezzatura" type="submit" value="Attrezzatura"
                              onclick="${this.getAttribute('tools')}"/>
                              &nbsp;&nbsp;&nbsp;
                          </li>
                      </ul>
                      </div>
                  </ul>
              </nav>
          </header>
      `;
    }
  }
  
  customElements.define('header-simple', Header);
  