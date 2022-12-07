package day07

data class DeviceFile(val name: String, val size:Int)

class Directory(private val name : String, val parent : Directory?){
    private lateinit var directories : List<Directory>
    private lateinit var files : List<DeviceFile>
    fun addContent(contents : List<String>){
        directoryRegister.add(this)
        directories = contents.map{it.split(" ")}.filter{it[0] == "dir"}.map{ Directory(it[1], this) }
        files = contents.map{it.split(" ")}.filter{it[0].toIntOrNull() != null}.map{ DeviceFile(it[1], it[0].toInt()) }
    }

    fun navigateTo(directoryName: String) : Directory {
        return directories.find { directoryName == it.name }!!
    }

    fun getSize(): Int {
        return files.sumOf { it.size } + directories.sumOf {it.getSize()}
    }

}