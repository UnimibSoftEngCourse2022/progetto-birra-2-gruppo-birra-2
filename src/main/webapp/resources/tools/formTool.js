class FormTool extends HTMLElement {
    constructor() {
      super();
    }
  
    connectedCallback() {
      this.innerHTML = `
            <div class="ContainerForm">
                <h1>La mia attrezzatura</h1>
                <form action="editUserTools" method="POST">
                    
                    <div id="container"></div>
                    
                    <input type="hidden" id="autore" name="autore" value="${this.getAttribute('author')}"/>
                    
                    
                    <div class="HStack" id="add" onclick="add_equip()">
                        <img src="${this.getAttribute('plusImage')}"></img>
                        <p>Aggiungi</p>
                    </div>
                    
                    <div class="buttonContainer">
                        <input type="submit" id="Invia" value="Salva"/>
                    </div>
                </form>
            </div>
      `;
    }
  }
  
  customElements.define('form-tool', FormTool);