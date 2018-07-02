Signup POST
https://proyectois.herokuapp.com/account/registration
{
    "firstName" : "nombre",
    "lastName" : "apellido",
    "dateOfBirth" : "DD/MM/AA",
	"email" : "email",
	"password" : "clave",
	"confirmationPassword" : "clave"
}

Login POST
https://proyectois.herokuapp.com/account/login
{
	"email" : "email",
	"password" : "clave"
}

update de una cuenta PUT
https://proyectois.herokuapp.com/update/{idDelusuario}
{
    "firstName" : "nombre",
    "lastName" : "apellido",
    "dateOfBirth" : "DD/MM/AA",
	"email" : "email",
	"password" : "clave",
	"confirmationPassword" : "clave"
}


añadir amigos PUT
https://proyectois.herokuapp.com/account/addFriend/{idDelUsuario}

{
	"friendId" : "id del amigo a anadir" 
}

crear un album  PUT
https://proyectois.herokuapp.com/album/create/{idDelUsuario}

{
	"accountId" : "id del usuario",
	"title" : "titulo",
	"description" : "descripcion"
}

crear una media PUT
https://proyectois.herokuapp.com/media/create/{idDelAlbum}

{
	"type" : "image/video/track",
	"albumId" : "id del album",
	"url" : "el url de la ubicacion de la imagen o video",
	"link" : "url de instagram"
}

buscar en instagram GET
https://proyectois.herokuapp.com/search/instagram?q=TagABuscar

tags que se pueden buscar:
#art #stencil #stencylart #arte #timelapseart #city #capitals #travel #world #video #timelapse 
#stencils #caracas #venezuela #blackandwhite #streetartccs #videoart #artecaracas #caracasarte #wall
#chalk #maduro #nicolasmaduro #blackboard #artecaracas #caracasarte #wall #takeyourpills

Busqueda en YT
https://proyectois.herokuapp.com/search/youtube?q=Busqueda
Se busca lo que se quiera, la api solo retorna 10 resultados.

Busqueda en Spotify
https://proyectois.herokuapp.com/search/spotify?q=Busqueda
Se busca lo que se quiera, la api solo retorna 10 resultados.