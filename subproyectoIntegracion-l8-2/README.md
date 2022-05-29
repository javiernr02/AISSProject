# CityEvents API 

API basada en ciudades y eventos:
Estos son los recursos
- Cada ciudad tiene un: identificador, nombre, descripcion y eventos asociados
- Cada evento tiene un: identificador, nombre, descripcion, organizador, categoria, localizacion, fecha, precio (si hubiera) y fqas asociados
- Cada fqa tiene un: identificador, pregunta y respuesta

Ejemplos JSON:

Recurso City:

{
	"id":"c1",
	"name":"Seville",
	"description":"Seville is a city with a continental Mediterranean climate with a large number of attractive places",
	"events":[
		{
			"id":"e1",
			"name:"Europa League Final",
			"description":"Silver competition of European international football after the Champions League",
			"organizer":"Uefa",
			"category":"Football",
			"location":"La Cartuja Stadium",
			"date:":"18/05, 21:00",
			"price":"40-200 euros",
		},
		]
			
}



Recurso Event:

{
	"id":"e1",
	"name:"Europa League Final",
	"description":"Silver competition of European international football after the Champions League",
	"organizer":"Uefa",
	"category":"Football",
	"location":"La Cartuja Stadium",
	"date:":"18/05, 21:00"
	"price":"40-200 euros"
}


Recurso FQA:

{
	"id": "f14",
	"question": "Can I take photos? Can it be filmed?",
	"answer": "Yes, you can take photos and make films inside the Stadium as long as the films are not used for commercial purposes."
	
}
