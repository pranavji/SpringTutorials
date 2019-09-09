
var pushSocket = new WebSocket("ws://202.88.244.214:6005/push")

pushSocket.onmessage = function (event) {

   notificationDTO = JSON.parse(event.data);
   if(notificationDTO.clueid!="-2")
   {
   winner= '<li th:if="${team.winner}" class="nav-item" > <a class="nav-link" href="#" data-toggle="button">  <i class="material-icons">announcement</i>Winner <div class="ripple-container"></div> </a></li>';
   $("#unlocked"+notificationDTO.clueid).text(notificationDTO.done);
   $("#unlocktime"+notificationDTO.clueid).text(notificationDTO.unlocktime);
  // alert(notificationDTO.winner);
   if(notificationDTO.winner==true){
   $("#teamcontainer"+notificationDTO.teamid).html(winner);
   }
   loadNotification();
   }
   else
   {
   alert(notificationDTO.message);

   }
//  alert(notificationDTO.teamid);
//
//   alert(notificationDTO.winner);

 //  alert(notificationDTO.message);

  //do ui update here
}

pushSocket.onopen = function (event) {
    //send empty message to initialize socket connnection
    pushSocket.send("connected");
};

pushSocket.onclose = function (event) {
    //send empty message to initialize socket connnection
    //alert("Socket Closed by Server");
};

function deleteClue(teamid,clueid)
{
if(confirm("Are you sure you want to delete the clue?"))
$.get('http://202.88.244.214:6005/ajax/deleteclue?clueid='+clueid+'&teamid='+teamid,
function (data) {
     location.reload(true);
 }
 );
}
function deleteTeam(teamid)
{
if(confirm("Are you sure you want to delete this Team? All subsequent clues will be deleted! "))
$.get('http://202.88.244.214:6005/ajax/deleteteam?&teamid='+teamid,
function (data) {
     location.reload(true);
 }
 );
}


  function openAddDialog(mode,id)
  {


     addTeamContent= '<div >  <form action="service/addteam">    <div class="form-group" style="margin-top:50px">      <label for="teamname">Team Name</label>'+
      '<input required="true" type="teamname" class="form-control" name="teamname" id="teamname">     </div>    <button style="position:absolute;bottom:20px;right:20px;" type="submit" class="btn btn-primary">Add</button>'+
        '</form></div>';




  if(mode=="addTeamContent")
  $("#dialogBox").html(addTeamContent);
  else if(mode=="addClueContent")
  {
  $("#xxteamid").val(id);
  $("#formmode").val("add");
  $("#dialogBox").html($("#addClueForm").html());

   }
   else if(mode=="editClueContent")
     {
      $("#dialogBox").html($("#addClueForm").html());
     $("#xxclueid").val(id);
   //  alert($("#xxclueid").val());
     $("#cluetitle").val($("#cluetitle"+id).text());
   //  alert($("#cluetitle").val());
     $("#clue").val($("#clue"+id).text());
    // alert($("#clue").val());
     $("#clueimage").val($("#clueimages"+id).val());
    // alert($("#clueimage").val());
     $("#password").val($("#cluepassword"+id).text());
   //  alert($("#password").val());
     $("#formmode").val("edit");
    // alert($("#formmode").val());

 $("#dialogContainer").fadeIn();

      }
   else if(mode=="viewClue")
  {


  $("#dialogBox").html("<img src='/QRCodes/qr"+id+".png'>");

   } else if(mode=="viewClueImage")
  {
   $("#dialogBox").html("<img src='/ClueImage/"+id+"' width='400px' >");


   }

  $("#dialogContainer").fadeIn();
  }
  function closeContainer()
  {
  $("#dialogContainer").fadeOut();
  }
     $(document).ready(function() {
            $(".no_target").click(function() {
                return false;
            });
        });
  function addClue(id)
  {
  openAddDialog("addClueContent",id);
  }

  function editClue(id)
  {
  openAddDialog("editClueContent",id);
  }

  function viewClue (id)
  {
  alert(id);
  openAddDialog("viewClue",id);
  }

  function viewClueImage (path)
  {

  openAddDialog("viewClueImage",path);
  }

  function loadNotification()
  {
    $.get(
    "http://202.88.244.214:6005/ajax/notification",
    function(data){
    $("#notificationContent").empty();
     content="<ul class='list-group'>";


    $.each(data, function(i, item) {

        content+='<a style="border-radius:5px;margin-bottom:20px;padding:25px" href="#" class="list-group-item active "><div class="row">'+data[i].message+'</div><div class="row" style="float:right;font-size:10px;">'+data[i].timeStamp+'</div></a>';

    });
     content+="</ul>";
     $("#notificationContent").html(content);

    }
    );
  }