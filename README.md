# MilkMachineProject

The goal of this project is API service of milk machines. The controller consists of a few endpoints:

-> GET endpoint which returns list of Milk machines,
-> POST endpoint which add Milk machine in @RequestBody parameter,
-> PUT endpoint which modifies Milk machine by @RequestBody parameter,
-> DELETE endpoint which removes chosen Milk machine by @PahVariable "id" of this machine,
-> POST endpoint which add sensor to chosen Milk machine by it's id @PathVariable "idMachine",
-> GET enpoint which return average temperature of chosen Machine by @PathVariable "idMachine".

The app was tested in Mockito framework.



