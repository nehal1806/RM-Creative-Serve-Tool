#!/usr/bin/perl

#=pod

my $orig;
#my $directory = '/Users/muktharahmed/Data2/toDelete/new_responses2/imai/android';
my $directory = $ARGV[0];
print "+++ Directory to work on: $directory \n";
opendir (DIR, $directory) or die $!;

while (my $file = readdir(DIR)) {

	@fileParams = split(/_/, $file);
	#print "@fileParams \n";
	print "$#fileParams \n";

	if ($#fileParams == 6) {
		$orig = $fileParams[0] . "_" . $fileParams[1] . "_" . $fileParams[2] . "_" .$fileParams[3] . "_" . $fileParams[4] . "_" . $fileParams[5] . $fileParams[6];
		print "6 param - \n";

	} elsif ($#fileParams == 5) {
		print "5 param - \n";
		$orig = $fileParams[0] . "_" . $fileParams[1] . "_" . $fileParams[2] . "_" .$fileParams[3] . "_" . $fileParams[4] . $fileParams[5];

	} elsif ($#fileParams == 4) {
		print "4 param - \n";
		$orig = $fileParams[0] . "_" . $fileParams[1] . "_" . $fileParams[2] . "_" . $fileParams[3] . $fileParams[4];

	} elsif ($#fileParams == 3) {
		print "3 param - \n";
		$orig = $fileParams[0] . "_" . $fileParams[1] . "_" . $fileParams[2] . $fileParams[3];
	}

	$mvCmd = "mv " . $directory . "/" . $file . " " . $directory . "/" . $orig;
	print "\n$mvCmd";
	system($mvCmd);

}
#=cut


=pod
my $directory = "/Users/muktharahmed/Data2/toDelete/new_responses2/imai/android/"; 
my $file = "banner_300X250_300X250_.new";
my $orig;

@fileParams = split(/_/, $file);
print "@fileParams \n";
print "$#fileParams \n";

if ($#fileParams == 6) {
	$orig = $fileParams[0] . "_" . $fileParams[1] . "_" . $fileParams[2] . "_" .$fileParams[3] . "_" . $fileParams[4] . "_" . $fileParams[5] . $fileParams[6];
	print "6 param - \n";

} elsif ($#fileParams == 5) {
	print "5 param - \n";
	$orig = $fileParams[0] . "_" . $fileParams[1] . "_" . $fileParams[2] . "_" .$fileParams[3] . "_" . $fileParams[4] . $fileParams[5];

} elsif ($#fileParams == 4) {
	print "4 param - \n";
	$orig = $fileParams[0] . "_" . $fileParams[1] . "_" . $fileParams[2] . "_" . $fileParams[3] . $fileParams[4];

} elsif ($#fileParams == 3) {
	print "3 param - \n";
	$orig = $fileParams[0] . "_" . $fileParams[1] . "_" . $fileParams[2] . $fileParams[3];
}

$mvCmd = "mv " . $directory . "/" . $file . " " . $directory . "/" . $orig;
print "\n$mvCmd";

=cut