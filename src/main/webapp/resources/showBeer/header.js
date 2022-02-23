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
                              <form action="brews" method="GET">
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
  
                      <div class="AddSearch">
                          <ul>
                              <li>
                                  <form class="ricerca" action="${this.getAttribute('search')}" method="POST" onsubmit="return blockSearch(event)">
                                      <input type="text" id="ricerca" name="ricerca" value="" placeholder="Cerca la ricetta"/>
  
                                      <input type="hidden" id="autore" name="autore" value="${this.getAttribute('author')}"/>
                                          &nbsp;&nbsp;
  
                                      <img src="${this.getAttribute('lente')}" alt="lente" id="LenteIcon" />
                                  </form>
                              </li>
                          </ul>
                      </div>
                  </ul>
              </nav>
          </header>
      `;
    }
  }
  
  customElements.define('header-search-beer', Header);
  
  
  function blockSearch(e) {
      ricercaRE = /^[^-\s][A-Za-z0-9!&()?'ìèé-ùàò_.:,;\s-]{3,}$/;
      var ricerca = document.getElementById('ricerca');
  
      if (ricerca.value == '') {
          e.preventDefault()
      }
  }
  