🧪 Test Android Básico
📖 Contexto

Redcomercio está preparando una demo de app interna para equipos de terreno.
Hoy el Dashboard muestra métricas básicas, pero necesitamos reemplazar su fragmento por una Lectura de QR que permita registrar visitas en terreno.

Las lecturas deben quedar en un historial con la hora exacta, y el Home debe mostrar el total acumulado de lecturas realizadas.

Queremos que mejores lo que haga falta y entregues los cambios mediante un Pull Request.

🧩 Historia: Lectura de QR

Contexto:
Queremos reemplazar el fragmento Dashboard por una pantalla que permita leer códigos QR y guardar las lecturas en un historial.
Además, en el Home debe mostrarse el total de lecturas realizadas.

🎯 Requerimientos
🔹 Lectura de QR

Cambiar el fragmento Dashboard por uno de Lectura de QR.

Agregar un botón que inicie la lectura usando cualquier librería (ZXing, ML Kit, etc).

Guardar cada lectura junto con la hora exacta.

🔹 Historial

Mostrar una lista con las lecturas realizadas (contenido + hora).

Ordenar de más reciente a más antigua.

🔹 Home

Mostrar el total de lecturas realizadas.

🔹 Extras

Corregir cualquier error que encuentres.

Dejar los cambios en un Pull Request hacia main.

✅ Criterios de aceptación

Puedo abrir la pantalla de Lectura y escanear un QR.

Cada lectura queda registrada con fecha y hora.

El Historial muestra las lecturas en orden descendente.

El Home muestra el total actualizado de lecturas.

La app no crashea y maneja correctamente los permisos de cámara.

🧠 Entrega

Crear branch: feature/qr-scan

Hacer commits claros y descriptivos.

Abrir un Pull Request con:

Breve descripción de los cambios.

Librería usada para escanear.
