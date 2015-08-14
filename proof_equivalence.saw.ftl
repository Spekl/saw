print "Extracting reference term \"${reference_function}\" from file \"${reference_file}\"";

l <- llvm_load_module "${reference_function}.bc";
ffs_ref <- llvm_extract l "${reference_function}" llvm_pure;

print "Extracting implementation term \"${test_function}\" from file \"${test_file}\"";
l2 <- llvm_load_module "${test_function}.bc";
ffs_imp <- llvm_extract l2 "${test_function}" llvm_pure;

print "Proving equivalence of ${reference_function} and ${test_function}";
let thm1 = {{ \x -> ffs_ref x == ffs_imp x }};
result <- prove abc thm1;
print result;

print "Finding bug via sat search";
let thm2 = {{ \x -> ffs_ref x != ffs_imp x }};
result <- sat abc thm2;
print result;

print "Done.";
 
