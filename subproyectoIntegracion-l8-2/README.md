# CityEvents API 

API basada en ciudades y eventos:
Estos son los recursos
- Cada ciudad tiene un: identificador, nombre, descripcion y eventos asociados
- Cada evento tiene un: identificador, nombre, descripcion, organizador, fecha, aforo máximo (si hubiera), precio (si hubiera), categoria e imágenes asociadas
- ¿Recurso imagen?

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
		
		{	

		
		}
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
