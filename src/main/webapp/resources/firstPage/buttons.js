class Buttons extends HTMLElement {
    constructor() {
      super();
    }
  
    connectedCallback() {
      this.innerHTML = `
        <style>

        .Buttons {
            display: flex;
            flex-direction: row;
            align-items: center;
            align-content: center;
            height: max-content;
            width: max-content;
            gap: 24px;
        }
        
        .Buttons input {
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 15px 20px;
            border-radius: 11px;
            font-size: 16px;
            font-weight: 700;
            cursor: pointer;
            text-align: center;
            border: none;
        }
        
        .Button1 input {
            color: black;
            background: transparent;
            padding: 0 0;
            border-radius: 0;
            transition: color 250ms ease-in-out, 
            transform 150ms ease;
        }
        
        .Button1 input:hover {
            color: #4E4F59;
        }
        
        .Button2 input {
            color: white;
            background: #FFCC00;
            transition: background 250ms ease-in-out, 
            transform 150ms ease;
        }
        
        .Button2 input:hover {
            background: black;
        }

        </style>
            <div class="Buttons">
                <form class="Button1" action="${this.getAttribute('submitTo1')}" method="GET">
                    <input type="submit" value="${this.getAttribute('value1')}"/>
                </form>

                <form class="Button2" action="${this.getAttribute('submitTo2')}" method="GET">
                    <input type="submit" value="${this.getAttribute('value2')}"/>
                </form>
            </div>
      `;
    }
  }
  
  customElements.define('header-buttons', Buttons);