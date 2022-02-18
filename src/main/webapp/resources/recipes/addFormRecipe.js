class FormRecipe extends HTMLElement {
    constructor() {
      super();
    }
  
    connectedCallback() {
      this.innerHTML = `
            <div class="VStack">
                <div class="ContainerForm">
                    <h1>Aggiungi una ricetta</h1>
                    
                    <form:form action="${this.getAttribute('action')}" method="POST" modelAttribute="r">
                        <div class="VStack">

                            <label class="insert" for="nome">Nome</label>
                            <input type="hidden" id="iD" name="iD" value="${this.getAttribute('id')}"/>
                            <input class="insert" type="text" id="nome" name="nome" value="${this.getAttribute('name')}" 
                                pattern="^[^-\s][A-Za-z0-9!&()?'ìèé-ùàò_.:,;\s-]{3,}$"
                                required 
                                oninvalid="this.setCustomValidity('Nome non consentito')"
                                oninput="this.setCustomValidity('')"/>
                            
                            <label class="insert" for="nome">Descrizione</label>
                            <textarea id="descrizione" name="descrizione">${this.getAttribute('description')}</textarea>
                            
                            <label class="insert" for="nome">Procedimento</label>
                            <textarea id="procedimento" name="procedimento">${this.getAttribute('process')}</textarea>
                            <input type="hidden" id="autore" name="autore" value="${this.getAttribute('author')}"/>
                            
                            <div class="HStack">
                                <input class="send" type="submit" id="submit" value="Avanti"/>
                            </div>

                        </div>
                    </form:form>
                </div>
            </div>
      `;
    }
  }
  
  customElements.define('form-recipe', FormRecipe);

  /*
function showErrorName(e) {
    nomeRE = /^[^-\s][A-Za-z0-9!&()?'ìèé-ùàò_.:,;\s-]{3,}$/;
    var nome = document.getElementById('nome');
    var descrizione = document.getElementById('descrizione');
    var procedimento = document.getElementById('procedimento');

    if (!nomeRE.test(nome.value) || nome.value === '') {
        e.preventDefault()
        nome.style.borderColor = "#FF3B30";
    } else {
        nome.style.borderColor = "#fafafa";
    }
}

*/