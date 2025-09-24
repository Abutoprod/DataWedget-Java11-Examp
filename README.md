# 📱 Esqueleto de App Java – Integração com DataWedge (Zebra TC22)

Este projeto é um **esqueleto de aplicação Android em Java** para integrar com o **DataWedge** da Zebra, especificamente testado no modelo **TC22**.  
Ele serve como base para capturar leituras de código de barras via DataWedge e exibi-las em tela.  

👉 Créditos ao [@darryncampbell](https://github.com/darryncampbell), de quem a base foi adaptada.  
👉 Este projeto foi **adaptado para controle via broadcast**, permitindo iniciar e parar o scanner por software (além dos botões físicos padrão).  

---

## 🚀 Funcionalidades

- Criação automática de **perfil DataWedge** para o app (via `DWUtilities`).
- Captura de dados de código de barras através de **intents do DataWedge**.
- Exibição do resultado em tela (`TextView`).
- Botão **Scan** com integração via **broadcast**:
  - Pressionar → envia broadcast `START_SCANNING`  
  - Soltar → envia broadcast `STOP_SCANNING`  
- Suporte ao gatilho físico já habilitado por padrão no TC22.  

---

## 📂 Estrutura principal

- `MainActivity.java`  
  - Inicializa a UI  
  - Cria o perfil DataWedge  
  - Recebe os intents de leitura  
  - Implementa botão de **Scan via broadcast**  

- `DWUtilities.java`  
  Classe utilitária para configurar o **perfil DataWedge**:
  - Habilita o plugin **BARCODE**  
  - Configura saída via **INTENT**  
  - Desabilita saída via **KEYSTROKE**  
  - Associa o perfil ao pacote do app  

---

## 🖥️ Exemplo de uso (MainActivity)

```java
@Override
public boolean onTouch(View view, MotionEvent motionEvent) {
    if (view.getId() == R.id.btnScan) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            // Pressionar → inicia scanner via broadcast
            Intent dwIntent = new Intent();
            dwIntent.setAction("com.symbol.datawedge.api.ACTION");
            dwIntent.putExtra("com.symbol.datawedge.api.SOFT_SCAN_TRIGGER", "START_SCANNING");
            sendBroadcast(dwIntent);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            // Soltar → para scanner via broadcast
            Intent dwIntent = new Intent();
            dwIntent.setAction("com.symbol.datawedge.api.ACTION");
            dwIntent.putExtra("com.symbol.datawedge.api.SOFT_SCAN_TRIGGER", "STOP_SCANNING");
            sendBroadcast(dwIntent);
        }
    }
    return true;
}
