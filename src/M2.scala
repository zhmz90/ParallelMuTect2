
import org.broadinstitute.gatk.queue.QScript
import org.broadinstitute.gatk.queue.extensions.gatk._

class run_M2_dream extends QScript {

  @Argument(shortName = "L",  required=false, doc = "Intervals file")
  var intervalsFile: List[File] = Nil
  @Argument(shortName = "normal",  required=true, doc = "Normal sample BAM")
  var normalBAM: String = ""
  @Argument(shortName = "tumor", required=true, doc = "Tumor sample BAM")
  var tumorBAM: String = ""
  @Argument(shortName = "o",  required=true, doc = "Output file")
  var outputFile: String = ""
  @Argument(shortName = "sc",  required=false, doc = "base scatter count")
  var scatter: Int = 70 
  /*
  @Argument(shortName = "cosmic",  required=true, doc = "cosmic vcf file ")
  var cosmic: String = ""
  @Argument(shortName = "pon",  required=true, doc = "vcf panel of normal file ")
  var pon: String = ""
   */
    def script() {

    val mutect2 = new MuTect2

    mutect2.reference_sequence = new File("/haplox/ref/GATK/ucsc.hg19/ucsc.hg19.fasta")
   /*
    mutect2.cosmic = new File(cosmic)
    mutect2.normal_panel = new File(pon)
    */
    mutect2.dbsnp = new File("/haplox/ref/GATK/knownsites/dbsnp_138.hg19.vcf")
    mutect2.intervalsString = intervalsFile
    mutect2.memoryLimit = 2
    mutect2.input_file = List(new TaggedFile(normalBAM, "normal"), new TaggedFile(tumorBAM, "tumor"))

    mutect2.scatterCount = scatter
    mutect2.out = outputFile
    add(mutect2)
  }

}
